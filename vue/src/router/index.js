import Vue from "vue";
import VueRouter from "vue-router";

// 뷰 어플리케이션에 라우터 플러그인을 추가한다.
Vue.use(VueRouter);

/* 생략 */
const router = new VueRouter({
  mode: "history",
  routes: [
    {
      path: "/sample",
      component: () => import("@/view/sample.vue"),
    },
    {
      path: "/sample2",
      component: () => import("@/view/sample2.vue"),
    },
    {
      path: "/AntTest",
      component: () => import("@/view/AntTest.vue"),
    },
    {
      path: "/mediaServer",
      component: () => import("@/view/mediaserver.vue"),
    },
    {
      path: "/camera",
      component: () => import("@/view/camera.vue"),
    },
    {
      path: "/recordServer",
      component: () => import("@/view/recordserver.vue"),
    },
    {
      path: "/Storage",
      component: () => import("@/view/storage.vue"),
    },
    {
      path: "/Map",
      component: () => import("@/view/map.vue"),
    },
  ],
});

router.beforeEach((to, from, next) => {
  console.log("beforeach,");
  console.log("to:", to);
  console.log("from:", from);
  next();
});

export default router;
