<template>
  <div id="app">
    <router-link to="/car">car</router-link>/
    <router-link to="/item">item</router-link>/
    <router-link to="/car_item">car Item</router-link>/
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "App",
};
</script>
