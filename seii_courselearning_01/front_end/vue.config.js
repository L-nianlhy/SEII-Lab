module.exports = {
  transpileDependencies: ["vuetify"],
  devServer: {
    proxy: {
      "^/api": {
        target: "http://localhost:8081/",
        ws: true,
        changeOrigin: true
        // pathRewrite: {
        // '^/api/': '/', // remove base path
        // },
      }
    },
    disableHostCheck: true
  }
};
