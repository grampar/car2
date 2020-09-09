import Vue from "vue";
import ddp from "./ddp.js";
import n from "./n3nsocket.js";
import router from "./router";
import gridmain from "./view/gridmain.vue";
import gridtree from "./view/gridtree.vue";
import alert from "./view/alert.vue";
import confirm from "./view/confirm.vue";
import search from "./view/search.vue";
import Antd from "ant-design-vue";
import App from "./App.vue";
import "ant-design-vue/dist/antd.css";

Vue.config.productionTip = false;

n.connect();

Vue.prototype.$ds = ddp;
Vue.prototype.n = n;

Vue.use(Antd);

Vue.component("gridmain", gridmain);
Vue.component("gridtree", gridtree);
Vue.component("alert", alert);
Vue.component("search", search);
Vue.component("confirm", confirm);

new Vue({
  render: (h) => h(App),
  router,
}).$mount("#app");
