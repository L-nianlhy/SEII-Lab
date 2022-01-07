<template>
  <!--未读消息列表 2021.07.01 刘红宇-->
  <div>
    <v-container>
      <v-card max-width="800">
        <v-toolbar color="indigo lighten-2" dark>
          <v-app-bar-nav-icon></v-app-bar-nav-icon>
          <v-toolbar-title>未读消息</v-toolbar-title>
          <v-spacer></v-spacer>
        </v-toolbar>
        <v-list subheader two-line>
          <v-list-item v-for="notice in notices" :key="notice.id">
            <v-list-item-content>
              <v-list-item-title v-text="'回复内容: '+notice.replyContent"></v-list-item-title>
              <v-list-item-subtitle v-text="'帖子主题: '+notice.postTheme">
              </v-list-item-subtitle>
              <v-list-item-subtitle v-text="'课程: '+notice.courseName">
              </v-list-item-subtitle>
              <v-list-item-subtitle v-text="'针对: '+notice.repliedContent">
              </v-list-item-subtitle>
              <v-list-item-subtitle v-text="'回复人: '+notice.replierName">
              </v-list-item-subtitle>
              <v-list-item-subtitle
                v-text="notice.replyTime"
              ></v-list-item-subtitle>
            </v-list-item-content>

            <v-list-item-action>
              <v-btn icon>
                <v-icon color="grey lighten-1" @click="toPostDetail(notice)"
                  >mdi-more</v-icon
                >
              </v-btn>
            </v-list-item-action>
          </v-list-item>
        </v-list>
      </v-card>
    </v-container>
  </div>
</template>

<script>
import { judgeStudent, judgeTeacher } from "@/util/auth";
import { getReplyNoticesByUser, setHaveRead } from "@/api/comment";

export default {
  name: "PostNoRead",
  data() {
    return {
      notices: []
    };
  },
  methods: {
    //前往帖子详情界面
    toPostDetail(notice) {
      const postId = notice.repliedPostId;
      const commentId = notice.commentId;
      // eslint-disable-next-line no-unused-vars
      setHaveRead(commentId).then(res => {
        if (judgeTeacher()) {
          this.$router.push({
            path: `/teacher/postDetail/${postId}`
          });
        } else if (judgeStudent()) {
          this.$router.push({
            path: `/student/postDetail/${postId}`
          });
        }
        location.reload();
      });
    }
  },
  mounted() {
    const uid = window.localStorage.getItem("userId");
    getReplyNoticesByUser(uid).then(res => {
      console.log(res);
      this.notices = res;
    });
  }
};
</script>

<style scoped></style>
