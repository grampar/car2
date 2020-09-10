import Vue from "vue";
import router from "./router";
import gridmain from "./components/gridmain.vue";
import gridtree from "./components/gridtree.vue";
import search from "./components/search.vue";
import axios from "axios";
import App from "./App.vue";

Vue.config.productionTip = false;

Vue.prototype.$http = axios;

Vue.component("gridmain", gridmain);
Vue.component("gridtree", gridtree);
Vue.component("search", search);

new Vue({
  render: (h) => h(App),
  router,
}).$mount("#app");
