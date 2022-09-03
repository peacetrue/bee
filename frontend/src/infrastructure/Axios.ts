import axios from 'axios';
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


instance.interceptors.response.use(function (response) {
  console.info("response: ", response);
  return response;
}, function (error) {
  console.info("response error: ", error);
  document.dispatchEvent(new CustomEvent(
    "feedback.show", {detail: error.message}
  ));
  return Promise.resolve(error);
});

export default instance;
