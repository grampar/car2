<template>
  <v-app id="inspire">
    <v-navigation-drawer
      v-model="drawer"
      :clipped="$vuetify.breakpoint.lgAndUp"
      app
    >
      <v-list dense>
        <template v-for="item in items">
          <v-row v-if="item.heading" :key="item.heading" align="center">
            <v-col cols="6">
              <v-subheader v-if="item.heading">
                {{ item.heading }}
              </v-subheader>
            </v-col>
            <v-col cols="6" class="text-center">
              <a href="#!" class="body-2 black--text">EDIT</a>
            </v-col>
          </v-row>
          <v-list-group
            v-else-if="item.children"
            :key="item.text"
            v-model="item.model"
            :prepend-icon="item.model ? item.icon : item['icon-alt']"
            append-icon=""
          >
            <template v-slot:activator>
              <v-list-item-content>
                <v-list-item-title>
                  {{ item.text }}
                </v-list-item-title>
              </v-list-item-content>
            </template>
            <v-list-item v-for="(child, i) in item.children" :key="i" link>
              <v-list-item-action v-if="child.icon">
                <v-icon>{{ child.icon }}</v-icon>
              </v-list-item-action>
              <router-link :to="child.to">
                <v-list-item-content>
                  <v-list-item-title>
                    {{ child.text }}
                  </v-list-item-title>
                </v-list-item-content>
              </router-link>
            </v-list-item>
          </v-list-group>
          <v-list-item v-else :key="item.text" link>
            <v-list-item-action>
              <v-icon>{{ item.icon }}</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title>
                {{ item.text }}
              </v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </template>
      </v-list>
    </v-navigation-drawer>

    <v-app-bar
      :clipped-left="$vuetify.breakpoint.lgAndUp"
      app
      color="blue darken-3"
      dark
    >
      <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
      <v-toolbar-title style="width: 300px" class="ml-0 pl-4">
        <span class="hidden-sm-and-down">박현수 매입/매출시스템</span>
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn icon large>
        <v-avatar size="32px" item>
          <v-img
            src="https://cdn.vuetifyjs.com/images/logos/logo.svg"
            alt="Vuetify"
          ></v-img
        ></v-avatar>
      </v-btn>
    </v-app-bar>
    <div id="loading" style="position:absolute;visibility:hidden;width:100%;height:100%;z-index:9999292929;background:white;opacity:0.5;align:center">
      <img src="/loading.gif">
    </div>
    <v-main>
      <v-container class="fill-height" fluid>
        <router-view></router-view>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
export default {
  props: {
    source: String,
  },
  data: () => ({
    dialog: false,
    drawer: null,
    items: [
      {
        icon: "mdi-chevron-up",
        "icon-alt": "mdi-chevron-down",
        text: "매출",
        model: true,
        children: [
          { icon: "mdi-plus", text: "item 관리", to: "/item" },
          { icon: "mdi-plus", text: "Car 관리", to: "/car" },          
          { icon: "mdi-plus", text: "자동차별 Item 관리", to: "/car_item" },
          { icon: "mdi-plus", text: "월별 자동차수량 입력", to: "/car_mon" },
          { icon: "mdi-plus", text: "월별 Rcar 입력", to: "/rcar" },
          { icon: "mdi-plus", text: "월별 ckd(캠스) 입력", to: "/ckd" },
          { icon: "mdi-plus", text: "월별 as 입력", to: "/asmon" },
          { icon: "mdi-plus", text: "월별 mobis 입력", to: "/mobis" },
          { icon: "mdi-plus", text: "매출현황", to: "/price_sum" },
        ],
      },
      {
        icon: "mdi-chevron-up",
        "icon-alt": "mdi-chevron-down",
        text: "매입",
        model: true,
        children: [
          { icon: "mdi-plus", text: "원재료 관리", to: "/source" },
          { icon: "mdi-plus", text: "원재료 아이템", to: "/source_item" },
          { icon: "mdi-plus", text: "매입 현황", to: "/source_sum" },
        ],
      },
    ],
  }),
};
</script>
