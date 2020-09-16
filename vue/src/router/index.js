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
    {
      path: "/item",
      component: () => import("@/view/item.vue"),
    },
    {
      path: "/car_item",
      component: () => import("@/view/car_item.vue"),
    },
    {
      path: "/rcar",
      component: () => import("@/view/rcar.vue"),
    },
    {
      path: "/ckd",
      component: () => import("@/view/ckd.vue"),
    },
    {
      path: "/asmon",
      component: () => import("@/view/asmon.vue"),
    },
    {
      path: "/price_sum",
      component: () => import("@/view/price_sum.vue"),
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
