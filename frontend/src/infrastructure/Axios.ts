import axios, {AxiosError} from 'axios';
import qs from 'qs';

let instance = axios.create({
  baseURL: `${process.env.REACT_APP_BASE_URL}`,
  paramsSerializer: function (params) {
    let string = qs.stringify(params, {allowDots: true});
    console.info("query string: ", string)
    return string;
  }
});

instance.interceptors.request.use(function (config) {
  // https://stackoverflow.com/questions/37708586/reactjs-disable-console-log-system-wide
  console.info("request config: ", config)
  // 默认使用 v1 版本接口
  !config.headers && (config.headers = {});
  !("Accept" in config.headers) && (config.headers.Accept = "application/vnd.bee.v1+json");
  return config;
}, function (error) {
  console.info("request error: ", error)
  return Promise.reject(error);
});

// 转向异常；如果正常，会被业务逻辑处理
instance.interceptors.response.use(function (response) {
  console.info("response: ", response);
  let data = response.data;
  if (!data) return response;
  if (!data.code || data.code === "success") return response;
  return Promise.reject(new AxiosError(
    data.message, data.code, response.config, response.request, response
  ));
});

// 捕获异常，直接返回的异常或者由上面的拦截器转出的异常。此拦截器正常返回 error 后，业务逻辑不会再处理
instance.interceptors.response.use(undefined, function (error) {
  console.info("response error: ", error);
  document.dispatchEvent(new CustomEvent("feedback.show", {detail: error.message}));
  // 使用 reject 返回，不能使用 resolve，否则业务认为是正常返回，然后使用 error 数据继续处理业务
  return Promise.reject(error);
});

export default instance;
