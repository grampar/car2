<template>
  <div id="app">
    <router-link to="/car">car 관리</router-link>/
    <router-link to="/item">item 관리</router-link>/
    <router-link to="/car_item">자동차별 Item 관리</router-link>/
    <router-link to="/rcar">월별 Rcar 입력</router-link>
/
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "App",
};
</script>

