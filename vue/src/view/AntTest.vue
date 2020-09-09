<template>
  <a-select
    mode="multiple"
    placeholder="Please select"
    style="width: 100%"
    @change="handleChange"
  >
    <a-select-option
      v-for="video in videoList"
      :key="video.Name"
      :value="video.Name"
    >
      {{ video.Name }}
    </a-select-option>
  </a-select>
</template>
<script>
export default {
  data() {
    return {
      videoList: [{ Name: "aaa" }, { Name: "bbb" }, { Name: "ccc" }],
    };
  },
  methods: {
    handleChange(value) {
      console.log(`selected ${value}`);
    },
  },
};
</script>
