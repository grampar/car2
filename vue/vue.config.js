// const path = require('path')
const webpack = require("webpack");

module.exports = {
  publicPath: "",
  filenameHashing: false,
  assetsDir: "static",
  configureWebpack: {
    plugins: [
      new webpack.ProvidePlugin({
        $: "jquery",
        jquery: "jquery",
        jQuery: "jquery",
        "window.jQuery": "jquery",
        "window.$": "jquery",
      }),
    ],
  },
  devServer: {
    host: "0.0.0.0",
    hot: true,
    disableHostCheck: true,
    port: 9021,
  },
};
