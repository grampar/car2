import n from "./n3nsocket.js";

class DDP {
  constructor() {
    console.log("I'm DDP!");
  }

  call(method, params, callback) {
    n.call(method, params, callback);
  }

  get(method, params) {
    //const me = this;
    return new Promise((resolve, reject) => {
      n.call(method, params, (e, d) => {
        if (e == null) {
          if (d.success || d.statusCode == 200) {
            let body;
            if(typeof d["body"]==="string") body = JSON.parse(d["body"]);
            else body=d["body"];
            
            resolve(body["result"]["body"]);
          } else {
            reject(d.message || d.statusMessage);
          }
        } else {
          reject(e);
        }
      });
    });
  }

  subscribe(channel, callback) {
    return n.socket.subscribe(channel, callback);
  }

  unsubscribe(channel, callback) {
    return n.socket.unsubscribe(channel, callback);
  }

  publish(channel, params) {
    return n.socket.publish(channel, params);
  }

  getToken() {
    return "";
  }

  getWizeyeUrl() {
    return "";
  }
}

export default new DDP();
