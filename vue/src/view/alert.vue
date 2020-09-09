<template>
  <!-- contents -->

  <div
    class="layer-wrap js-layer-opened"
    data-layer="modal-alert-title"
    tabindex="0"
    style="display: block; z-index: 10019; opacity: 1;"
    v-show="isShow === 'visible'"
  >
    <div class="layer-container">
      <section class="ui-layer ui-layer--alert">
        <div class="ui-layer__head">
          <h2 class="ui-layer__title">경고</h2>
        </div>
        <div class="ui-layer__body">
          <div class="ui-layer__body-inner mCustomScrollbar _mCS_4 mCS_no_scrollbar">
            <div
              id="mCSB_4"
              class="mCustomScrollBox mCS-light mCSB_vertical mCSB_inside"
              tabindex="0"
              style="max-height: none;"
            >
              <div
                id="mCSB_4_container"
                class="mCSB_container mCS_y_hidden mCS_no_scrollbar_y"
                style="position:relative; top:0; left:0;"
                dir="ltr"
              >
                <p class="ui-layer__alert-text">{{ message }}</p>
              </div>
              <div
                id="mCSB_4_scrollbar_vertical"
                class="mCSB_scrollTools mCSB_4_scrollbar mCS-light mCSB_scrollTools_vertical"
                style="display: none;"
              >
                <div class="mCSB_draggerContainer">
                  <div
                    id="mCSB_4_dragger_vertical"
                    class="mCSB_dragger"
                    style="position: absolute; min-height: 30px; top: 0px; height: 0px;"
                  >
                    <div class="mCSB_dragger_bar" style="line-height: 30px;"></div>
                  </div>
                  <div class="mCSB_draggerRail"></div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="ui-layer__foot">
          <div class="btns-box">
            <button type="button" class="btn btn-c-primary" @click="alertClose">확인</button>
          </div>
        </div>
      </section>
    </div>
  </div>

  <!-- //contents -->
</template>

<script>
export default {
  name: "alert",
  props: {
    isShow: String,
    message: String,
  },
  data() {
    return {};
  },
  methods: {
    alertClose() {
      this.$emit("alertclose", "hidden");
    },
  },
};
</script>
