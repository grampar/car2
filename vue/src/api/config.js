const hostname = location.hostname;
//const port = location.port
const port = 9020;
const BASE_URL = "http://" + hostname + ":" + port;
console.log("==== Base Server URL : " + BASE_URL + " ====");
export default BASE_URL;
