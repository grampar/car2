<template>
  <!-- contents -->

  <div
    class="layer-wrap js-layer-opened"
    data-layer="modal-alert"
    tabindex="0"
    style="display: block; z-index: 10004; opacity: 1;"
    v-show="isshow === 'visible'"
  >
    <div class="layer-container">
      <section class="ui-layer ui-layer--alert">
        <div class="ui-layer__body">
          <div class="ui-layer__body-inner mCustomScrollbar _mCS_3 mCS_no_scrollbar">
            <div
              id="mCSB_3"
              class="mCustomScrollBox mCS-light mCSB_vertical mCSB_inside"
              tabindex="0"
              style="max-height: none;"
            >
              <div
                id="mCSB_3_container"
                class="mCSB_container mCS_y_hidden mCS_no_scrollbar_y"
                style="position:relative; top:0; left:0;"
                dir="ltr"
              >
                <p class="ui-layer__alert-text">{{ message }}</p>
              </div>
              <div
                id="mCSB_3_scrollbar_vertical"
                class="mCSB_scrollTools mCSB_3_scrollbar mCS-light mCSB_scrollTools_vertical"
                style="display: none;"
              >
                <div class="mCSB_draggerContainer">
                  <div
                    id="mCSB_3_dragger_vertical"
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
            <button type="button" class="btn btn-c-secondary" @click="noClose">아니오</button>
            <button type="button" class="btn btn-c-primary" @click="yesClose">네</button>
          </div>
        </div>
      </section>
    </div>
  </div>

  <!-- //contents -->
</template>

<script>
export default {
  name: "confirm",
  props: {
    isshow: String,
    message: String,
    callback: Function,
  },
  data() {
    return {};
  },
  methods: {
    yesClose() {
      this.callback();
      this.$emit("confirmclose", "yes");
    },
    noClose() {
      this.$emit("confirmclose", "no");
    },
  },
};
</script>
