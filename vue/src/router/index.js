import Vue from "vue";
import VueRouter from "vue-router";

// 뷰 어플리케이션에 라우터 플러그인을 추가한다.
Vue.use(VueRouter);

/* 생략 */
const router = new VueRouter({
  mode: "history",
  routes: [
    {
      path: "/car",
      component: () => import("@/view/car.vue"),
    },
  ],
});
/* 
router.beforeEach((to, from, next) => {
  console.log("beforeach,");
  console.log("to:", to);
  console.log("from:", from);
  next();
});
 */
export default router;
