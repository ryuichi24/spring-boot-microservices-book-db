#!/bin/bash

# container registry
# https://docs.microsoft.com/en-us/azure/container-registry/container-registry-get-started-azure-cli

# k8s services
# https://docs.microsoft.com/en-us/azure/aks/virtual-nodes-cli

readonly AZ_RESOURCE_GROUP="myFirstRG"
readonly AZ_RESOURCE_GROUP_LOCATION="EastUS"
# ACR variables
tmp="testcontainerregistry$(echo ${AZ_RESOURCE_GROUP}$(uuidgen) | tr A-Z a-z | sed 's/\-//g')"
readonly AZ_ACR_NAME=${tmp:0:50}
readonly AZ_ACR_SKU="Basic"
tmp="testk8scluster$(echo ${AZ_RESOURCE_GROUP}$(uuidgen) | tr A-Z a-z | sed 's/\-//g')"
readonly AZ_CLUSTER_NAME=${tmp:0:50}
readonly AZ_CLUSTER_NODE_COUNT="2"
tmp="testworkspace$(echo ${AZ_RESOURCE_GROUP}$(uuidgen) | tr A-Z a-z | sed 's/\-//g')"
readonly AZ_WORKSPACE_NAME_FOR_LOG_ANALITICS=${tmp:0:50}
tmp="testvnet$(echo ${AZ_RESOURCE_GROUP}$(uuidgen) | tr A-Z a-z | sed 's/\-//g')"
readonly AZ_VNET=${tmp:0:50}
tmp="testsubnet$(echo ${AZ_RESOURCE_GROUP}$(uuidgen) | tr A-Z a-z | sed 's/\-//g')"
readonly AZ_SUBNET=${tmp:0:50}
tmp="testsubnetforvirtualnodes$(echo ${AZ_RESOURCE_GROUP}$(uuidgen) | tr A-Z a-z | sed 's/\-//g')"
readonly AZ_SUBNET_FOR_VIRTUAL_NODES=${tmp:0:50}

# check if providers are registed in the current subscription
az provider show -n Microsoft.OperationsManagement -o table
az provider show -n Microsoft.OperationalInsights -o table
az provider show -n Microsoft.Insights -o table

# if not resgisted
# az provider register --namespace Microsoft.OperationsManagement
# az provider register --namespace Microsoft.OperationalInsights
# az provider register --namespace Microsoft.Insights

# check if the specified resource group already exists if not create a new one
echo "Checking the specifed resource group..."
RG_FOUND=$(az group show -g ${AZ_RESOURCE_GROUP} -o tsv --query "properties.provisioningState")
if [ "${RG_FOUND}" = "Succeeded" ]; then
    echo "The resource group: ${AZ_RESOURCE_GROUP} already exists."
    exit
else
    echo "Creating a new resource group..."
    az group create \
        --name ${AZ_RESOURCE_GROUP} \
        --location ${AZ_RESOURCE_GROUP_LOCATION}
    echo "Done!"
fi

# create a workspace for Minitor Logs
# https://docs.microsoft.com/en-us/azure/azure-monitor/logs/azure-cli-log-analytics-workspace-sample
echo "creating a workspace for Minitor Logs..."
workspaceid=$(
    az monitor log-analytics workspace create \
        --resource-group ${AZ_RESOURCE_GROUP} \
        --workspace-name ${AZ_WORKSPACE_NAME_FOR_LOG_ANALITICS} \
        -o tsv --query "id"
)
echo "Done!"

#
echo "creating a virtual network: ${AZ_VNET} ..."
az network vnet create \
    --resource-group ${AZ_RESOURCE_GROUP} \
    --name ${AZ_VNET} \
    --address-prefixes 10.0.0.0/8 \
    --subnet-name ${AZ_SUBNET} \
    --subnet-prefix 10.240.0.0/16
#
echo "creating a subnet: ${AZ_SUBNET} for virtual nodes..."
az network vnet subnet create \
    --resource-group ${AZ_RESOURCE_GROUP} \
    --vnet-name ${AZ_VNET} \
    --name ${AZ_SUBNET_FOR_VIRTUAL_NODES} \
    --address-prefixes 10.241.0.0/16

subnetid=$(az network vnet subnet show \
    --resource-group ${AZ_RESOURCE_GROUP} \
    --vnet-name ${AZ_VNET} \
    --name ${AZ_SUBNET} \
    --query id -o tsv)

# create azure container registry resource
echo "Creating new container registry: ${AZ_ACR_NAME}..."
acrid=$(az acr create \
    --resource-group ${AZ_RESOURCE_GROUP} \
    --name ${AZ_ACR_NAME} \
    --sku ${AZ_ACR_SKU} \
    --query id -o tsv)

# create azure kubernetes service resource
echo "Creating new kubernetes service cluster: ${AZ_CLUSTER_NAME}..."
az aks create \
    -g ${AZ_RESOURCE_GROUP} \
    -n ${AZ_CLUSTER_NAME} \
    --enable-managed-identity \
    --node-count ${AZ_CLUSTER_NODE_COUNT} \
    --workspace-resource-id ${workspaceid} \
    --generate-ssh-keys \
    --enable-addons monitoring \
    --network-plugin azure \
    --vnet-subnet-id ${subnetid} \
    --attach-acr ${acrid}
# --enable-msi-auth-for-monitoring \

#
az aks enable-addons \
    --resource-group ${AZ_RESOURCE_GROUP} \
    --name ${AZ_CLUSTER_NAME} \
    --addons virtual-node \
    --subnet-name ${AZ_SUBNET_FOR_VIRTUAL_NODES}

cat <<EOF
# Delete the resource group
az group delete \
--resource-group ${AZ_RESOURCE_GROUP} -y

# download credentials for the cluster
az aks get-credentials --resource-group ${AZ_RESOURCE_GROUP} --name ${AZ_CLUSTER_NAME}

# to switch
https://github.com/MicrosoftDocs/azure-docs/issues/8070

# get nodes
kubectl get nodes
EOF
