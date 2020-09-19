import axios from 'axios'
import BASE_URL from '@/api/config'

const apiConfig = axios.create({
  baseURL: BASE_URL,
  headers: {
    // 'Accept': 'application/json',
    'Content-Type': 'application/json'
  }
})

apiConfig.interceptors.request.use(function(config) {
  // Do something before request is sent
  document.getElementById("loading").style.visibility="visible"
  return config;
}, function(error) {
  // Do something with request error
  console.log('Error');
  return Promise.reject(error);
});

apiConfig.interceptors.response.use(function(response) {
  // Do something with response data  
  document.getElementById("loading").style.visibility="hidden"
  return response;
}, function(error) {
  // Do something with response error
  console.log('Error fetching the data');
  return Promise.reject(error);
});


export default apiConfig
