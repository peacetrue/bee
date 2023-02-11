#!/bin/bash

set -x
./env-set.sh

pwd=$(pwd)
# 根据 Shell 环境变量，初始化 workflows 环境变量
cd "$pwd/.github/workflows" || exit
back_envs=(DEV LOCAL PROD)
for app in FRONTEND MAIN MONITOR ; do
    for env in "${back_envs[@]}" ; do
      name="BEE_${app}_URL_${env}"
      value=$(printenv "$name")
      sed -i "s|${name}.*|${name}: $value|g" macOS.yml
    done
done

# 根据 Shell 环境变量，初始化文档环境变量
cd "$pwd/docs/antora" || exit
sed -i "/BEE/d" "antora.yml"
for env in $(printenv | grep BEE | grep -v BEE_REMOTE) ; do
    sed -i "/start_page/i${env//=/: }" "antora.yml"
done
#https://unix.stackexchange.com/questions/552689/multiline-variable-add-a-tab-before-each-newline
(TAB=$'    ' ; sed -i "s/^BEE/${TAB}BEE/" "antora.yml")

# 根据 Shell 环境变量，初始化前端环境变量
cd "$pwd/frontend" || exit
#https://stackoverflow.com/questions/1494178/how-to-define-hash-tables-in-bash
#https://stackoverflow.com/questions/6047648/associative-arrays-error-declare-a-invalid-option
#https://www.cnblogs.com/yy3b2007com/p/11267237.html
front_envs=(development native production)
for (( i = 0; i < ${#front_envs[@]}; i++ )); do
  front_env=${front_envs[i]}
  back_env=${back_envs[i]}
  sed -i "s|REACT_APP_API_URL.*|REACT_APP_API_URL='$(printenv "BEE_MAIN_URL_${back_env}")'|g" ".env.$front_env"
  sed -i "s|REACT_APP_SWAGGER_URL.*|REACT_APP_SWAGGER_URL='$(printenv "BEE_MAIN_URL_${back_env}")/swagger-ui/index.html'|g" ".env.$front_env"
  sed -i "s|REACT_APP_MONITOR_URL.*|REACT_APP_MONITOR_URL='$(printenv "BEE_MONITOR_URL_${back_env}")'|g" ".env.$front_env"
done

# 根据 Shell 环境变量，初始化 nginx 配置
sed "s|BEE_HOST|$BEE_HOST_LOCAL|g;s|BEE_MONITOR_PORT|$BEE_MONITOR_PORT|g;s|BEE_MAIN_PORT|$BEE_MAIN_PORT|g;"  bee.peacetrue.tpl.conf >> bee.peacetrue.local.conf
sed "s|BEE_HOST|$BEE_HOST_PROD|g;s|BEE_MONITOR_PORT|$BEE_MONITOR_PORT|g;s|BEE_MAIN_PORT|$BEE_MAIN_PORT|g;"  bee.peacetrue.tpl.conf >> bee.peacetrue.cn.conf