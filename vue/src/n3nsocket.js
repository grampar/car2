import SockJS from "sockjs-client";

var n = {
  callBack: null,
  sock: null,
  call: function(method, params, callback) {
    this.callBack = callback;
    this.send(method, params);
  },
  connect: function() {
    this.sock = new SockJS("http://localhost:9020/echo");
  },
  onmessage: function() {
    // 2. connection이 맺어지면 실행된다.
    const me = this;
    this.sock.onmessage = function(evt) {
      try {
        let tmp = evt.data.substring(1);
        let data = JSON.parse(tmp);

        me.callBack(null, data);
      } catch (e) {
        me.callBack(e, null);
      }
    };
  },
  onopen: function(event) {
    console.log("open:" + event);
  },
  send: function(method, params) {
    let sendParam = {
      msg: "method",
      id: 1,
      method: method,
      params: params,
    };

    let message = JSON.stringify(sendParam);
    this.sock.send(message);
    this.onmessage();
  },
};

export default n;
