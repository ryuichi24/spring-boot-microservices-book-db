FROM ubuntu:22.04

ARG USERNAME
ARG USER_GROUP_NAME
ARG USER_UID
ARG USER_GID
ARG TMP_PKG

# general packages
ARG PKG="sudo git vim curl zip unzip locales ${TMP_PKG}"

SHELL ["/bin/bash", "-c"]

RUN apt-get update \
    && apt-get install -y ${PKG} \
    && groupadd --gid ${USER_GID} ${USER_GROUP_NAME} \
    && useradd --uid ${USER_UID} --shell /bin/bash --gid ${USER_GID} -m ${USERNAME} \
    && echo %${USER_GROUP_NAME} ALL=\(ALL\) NOPASSWD:ALL > /etc/sudoers.d/${USER_GROUP_NAME} \
    && chmod 0440 /etc/sudoers.d/${USER_GROUP_NAME}

# setup locale https://stackoverflow.com/a/55077451/13723015
RUN echo "LC_ALL=en_US.UTF-8" >> /etc/environment \
    && echo "en_US.UTF-8 UTF-8" >> /etc/locale.gen \
    && echo "LANG=en_US.UTF-8" > /etc/locale.conf \
    && locale-gen en_US.UTF-8

# setup timezone https://dev.to/grigorkh/fix-tzdata-hangs-during-docker-image-build-4o9m
ARG TIME_ZONE
RUN ln -snf /usr/share/zoneinfo/${TIME_ZONE} /etc/localtime && echo ${TIME_ZONE} > /etc/timezone

# ---------------------------------- pyenv ----------------------------------
# dependencies for pyenv
ARG PYENV_DEP="make build-essential libssl-dev zlib1g-dev \
    libbz2-dev libreadline-dev libsqlite3-dev wget llvm libncurses5-dev \
    libncursesw5-dev xz-utils tk-dev libffi-dev liblzma-dev python3-openssl"

# default python version
ARG PYTHON_VERSION=3.10.5

ENV PYENV_ROOT=${HOME}/.pyenv

RUN apt-get install -y --no-install-recommends ${PYENV_DEP} \
    && su ${USERNAME} --command \
    # install pyenv https://k0nze.dev/posts/install-pyenv-venv-vscode
    ' \
    git clone https://github.com/pyenv/pyenv.git ${HOME}/.pyenv \
    && echo "export PYENV_ROOT=\${HOME}/.pyenv" >> ${HOME}/.bashrc \
    && echo "export PATH=\${PYENV_ROOT}/bin:\${PATH}" >> ${HOME}/.bashrc \
    && echo "eval \"\$(pyenv init --path)\"" >> ${HOME}/.bashrc \
    && export PYENV_ROOT=${HOME}/.pyenv \
    && export PATH=${PYENV_ROOT}/bin:${PATH} \
    && pyenv install "${PYTHON_VERSION}" \
    && pyenv global "${PYTHON_VERSION}" \
    '

# ---------------------------------- sdkman ----------------------------------
# https://sdkman.io/install
ARG JAVA_VERSION=18.0.2-amzn
ARG GRADLE_VERSION="7.5"

RUN su ${USERNAME} --command \
    'curl -s "https://get.sdkman.io" | bash \
    && source "${HOME}/.sdkman/bin/sdkman-init.sh" \
    && sdk install java "${JAVA_VERSION}" \
    && sdk install gradle ${GRADLE_VERSION}'

# ---------------------------------- nvm ----------------------------------
ARG NODE_VERSION=v18.7.0

RUN su ${USERNAME} --command \
    'curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.1/install.sh | bash \
    && source ~/.nvm/nvm.sh \
    && nvm install "${NODE_VERSION}" \
    && nvm alias default "${NODE_VERSION}" \
    && nvm use default'
