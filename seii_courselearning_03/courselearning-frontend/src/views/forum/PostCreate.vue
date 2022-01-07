<template>
  <div>
    <!--发布帖子页面 刘红宇 2021.07.01-->

    <!-- alert -->
    <v-alert
      class="alert"
      outlined
      type="success"
      text
      v-show="showSuccessDialog"
      transition="scroll-y-transition"
    >
      帖子发布成功！
    </v-alert>
    <!-- alert -->
    <v-alert
      class="alert"
      outlined
      type="warning"
      text
      v-show="showFailDialog"
      transition="scroll-y-transition"
    >
      {{ msg }}
    </v-alert>
    <v-container class="pl-16 pr-16">
      <form class="pa-12 grey lighten-5 mt-8">
        <v-text-field v-model="title" label="帖子标题(必填)"></v-text-field>
        <v-textarea v-model="message" label="帖子内容(必填)"></v-textarea>
        <v-btn class="ml-0 mt-8 info" @click="submit">
          发布
        </v-btn>
        <div>
          <v-btn class="ml-0 mt-8 info" @click="cancel">
          取消
          </v-btn>
        </div>
      </form>
    </v-container>

    <!-- 提示对话框 -->
    <v-dialog v-model="dialog" width="500">
      <v-card>
        <v-card-title> </v-card-title>

        <v-card-text>
          贴子发布成功。
        </v-card-text>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" text @click="dialog = false">
            确认
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import { getCourseById } from "@/api/course";
import { createPost } from "@/api/post";

export default {
  name: "PostCreate",
  data() {
    return {
      courseInfo: {}, //课程信息
      types: ["初级", "中级", "高级"],
      showSuccessDialog: false,
      showFailDialog: false,
      title: "", //帖子标题
      message: "", //帖子内容
      msg: ""
    };
  },
  methods: {
    //加载课程
    loadCourse() {
      const { courseId } = this.$route.params;
      const uid = window.localStorage.getItem("userId");
      getCourseById({ uid, courseId }).then(res => {
        this.courseInfo = res;
      });
    },
    //发布帖子
    submit() {
      const uid = window.localStorage.getItem("userId");
      const uname = window.localStorage.getItem("username");
      const payload = {
        postTime: null,
        title: this.title,
        message: this.message,
        userId: uid,
        userName: uname,
        courseId: this.courseInfo.id,
        latestReplyTime: null
      };
      createPost(payload).then(res => {
        if (res.code === 1) {
          this.showSuccessDialog = true;
          setTimeout(() => {
            this.showSuccessDialog = false;
          }, 1000);
          setTimeout(() => {
            window.history.go(-1);
          }, 1000);
        } else {
          this.showFailDialog = true;
          this.msg = res.msg;
          setTimeout(() => {
            this.showFailDialog = false;
          }, 1000);
        }
      });
    },
    cancel(){
      window.history.go(-1);
    }
  },
  mounted() {
    this.loadCourse();
  }
};
</script>

<style scoped></style>
