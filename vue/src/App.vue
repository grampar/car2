<template>
  <div id="app">
    <router-link to="/sample">sample</router-link>/
    <router-link to="/sample2">sample2</router-link>/
    <router-link to="/AntTest">AntTEst</router-link>/
    <router-link to="/camera">camera</router-link>/
    <router-link to="/mediaserver">mediaserver</router-link>/
    <router-link to="/recordserver">recordserver</router-link>/
    <router-link to="/storage">storage</router-link>/
    <router-link to="/map">map</router-link>
/
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "App",
};
</script>
