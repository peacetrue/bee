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
  console.info("request config: ", config)
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
  return Promise.resolve(error);
});


export default instance;
