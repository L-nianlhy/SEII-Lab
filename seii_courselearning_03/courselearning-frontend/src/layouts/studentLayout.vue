<template>
  <div>
    <v-app-bar color="deep-purple lighten-3" dense dark>
      <v-app-bar-nav-icon @click="direct('/student')">
        <v-icon>mdi-home</v-icon></v-app-bar-nav-icon
      >

      <v-toolbar-title @click="direct('/student')" class="cursor">
        Course Learning Student
      </v-toolbar-title>

      <v-spacer></v-spacer>
      <div v-show="hasCommentNoRead">您有新的未读消息</div>

      <v-app-bar-nav-icon @click="direct('/student/postNoRead')">
        <v-icon>mdi-bell</v-icon></v-app-bar-nav-icon
      >
      <v-menu left bottom>
        <template v-slot:activator="{ on, attrs }">
          <v-btn icon v-bind="attrs" v-on="on">
            <v-icon>mdi-dots-vertical</v-icon>
          </v-btn>
        </template>

        <v-list>
          <v-list-item
            v-for="opt in optionList"
            :key="opt.optionName"
            @click="direct(opt.link)"
          >
            <v-list-item-title>{{ opt.optionName }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </v-app-bar>
    <router-view />
  </div>
</template>

<script>
import { getReplyNoticesByUser } from "@/api/comment";

export default {
  name: "StudentLayout",
  data() {
    return {
      optionList: [
        {
          optionName: "历史订单",
          link: "/student/history"
        },
        {
          optionName: "个人中心",
          link: `/student/user/${window.localStorage.getItem("userId")}`
        },
        {
          optionName: "登出",
          link: "/"
        }
      ],
      hasCommentNoRead: false
    };
  },
  methods: {
    direct(link) {
      if (link === "/") {
        this.logout();
      }
      if (this.$route.path !== link) {
        this.$router.push(link);
      }
    },

    logout() {
      window.localStorage.removeItem("userId");
      window.localStorage.removeItem("userPhone");
      window.localStorage.removeItem("username");
      window.localStorage.removeItem("userRole");
    },

    isHasCommentNoRead() {
      this.hasCommentNoRead = false;
      const uid = window.localStorage.getItem("userId");
      getReplyNoticesByUser(uid).then(res=>{
        const CommentNoRead = res;
        console.log(CommentNoRead);
        if (CommentNoRead.length !== 0) {
          this.hasCommentNoRead = true;
        }
      })
    }
  },
  mounted() {
    this.isHasCommentNoRead();
  }
};
</script>

<style scoped>
.cursor {
  cursor: pointer;
}
</style>
