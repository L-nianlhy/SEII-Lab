<template>
  <!--  帖子详情界面 赵喆德 2021.07.04-->
  <div>
    <v-alert
      class="alert"
      outlined
      type="success"
      text
      v-show="showSuccessDialog"
      transition="scroll-y-transition"
    >
      评论成功！
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
    <v-container>
      <v-row>
        <v-col>
          <div style="font-size:200%;">
            <b>{{ post.title }}</b> <br />
          </div>
          <v-text-field
            style="width: 850px"
            v-model="post.userName"
            label="发帖人"
            readonly
          ></v-text-field>
          <v-text-field
              style="width: 850px"
              v-model="post.postTime"
              label="发帖时间"
              readonly
          ></v-text-field>
          <v-textarea
            style="width: 850px"
            v-model="post.message"
            label="帖子内容"
            readonly
          ></v-textarea>
          <v-toolbar color="indigo lighten-2" dark width="850px">
            <v-app-bar-nav-icon></v-app-bar-nav-icon>
            <v-toolbar-title>评论区</v-toolbar-title>
            <v-spacer></v-spacer>
          </v-toolbar>
          <div style="width: 850px">
            <v-list>
              <v-list-item v-for="comment in comments" :key="comment.id">
                <div v-if="comment.repliedId == null">
                  <v-list-item-content>
                    <ul
                      style="list-style-type: none;width: 850px"
                      class="comment"
                    >
                      <li style="color: darkblue;float: left">
                        {{ comment.userName }}:
                      </li>
                      <br />
                      <li style="float: left;">{{ comment.content }}</li>
                      <li
                        style="float: right;font-size: 80%;color: #666;margin-right: 5px;position: relative;"
                      >
                        <br />评论时间：{{ comment.createTime }}
                        <input
                          style="color:#000;font-weight: bold"
                          type="submit"
                          value=" 回复"
                          @click="display(comment.id)"
                        />
                      </li>
                    </ul>
                    <div class="textInput" style="display: none">
                      <input
                        type="button"
                        value="回复"
                        class="btm"
                        style="float: right;"
                        @click="postReply(comment)"
                      />
                      <v-textarea
                        class="form-reply"
                        style="resize: none;width: 780px"
                        v-model="currentReply"
                      >
                      </v-textarea>
                    </div>
                  </v-list-item-content>
                </div>
                <div v-else>
                  <v-list-item-content>
                    <ul
                      style="list-style-type: none;width: 850px"
                      class="comment"
                    >
                      <li style="color: darkblue;float: left">
                        {{ comment.userName }}
                      </li>
                      <li style="color: #666;float: left;margin:0 5px 0 5px;">
                        回复
                      </li>
                      <li style="color: deeppink;float: left">
                        {{ comment.repliedUserName+' "'+comment.repliedContent+'"' }}:
                      </li>
                      <br />
                      <li style="float: left;">{{ comment.content }}</li>
                      <li
                        style="float: right;font-size: 80%;color: #666;margin-right: 5px;position: relative;"
                      >
                        <br />评论时间：{{ comment.createTime }}
                        <input
                          style="color:#000;font-weight: bold"
                          type="submit"
                          value=" 回复"
                          @click="display(comment.id)"
                        />
                      </li>
                    </ul>
                    <div class="textInput" style="display: none">
                      <input
                        type="button"
                        value="回复"
                        class="btm"
                        style="float: right;"
                        @click="postReply(comment)"
                      />
                      <v-textarea
                        class="form-reply"
                        style="resize: none;width: 780px"
                        v-model="currentReply"
                      >
                      </v-textarea>
                    </div>
                  </v-list-item-content>
                </div>
              </v-list-item>
            </v-list>
          </div>
          <div>
            <div class="form-group">
              <label>评论内容<br /></label>
              <v-textarea
                class="form-control"
                style="resize: none"
                v-model="currentContent"
              ></v-textarea>
              <input
                type="button"
                value="发表评论"
                class="btm"
                @click="postComment"
              />
            </div>
          </div>
        </v-col>
        <v-col class="courseMessage" style="margin-left: 30px;">
          <form style="position: sticky;top: 0">
            <v-text-field
              v-model="courseInfo.name"
              label="课程名称"
              readonly
            ></v-text-field>
            <v-text-field
              v-model="courseInfo.id"
              label="课程ID"
              readonly
            ></v-text-field>
            <v-select
              :items="types"
              v-model="courseInfo.type"
              label="课程分类"
              readonly
            ></v-select>
            <v-textarea
              v-model="courseInfo.intro"
              label="课程简介"
              readonly
            ></v-textarea>
            <v-text-field
              v-model="courseInfo.school"
              label="所属学校"
              readonly
            ></v-text-field>
            <v-text-field
              v-model="courseInfo.createTime"
              label="创建时间"
              readonly
            ></v-text-field>
            <v-text-field
              v-model="courseInfo.teacherName"
              label="创建教师"
              readonly
            ></v-text-field>
          </form>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import { getCourseById } from "@/api/course";
import { createComment, getCommentsOrderByTime } from "@/api/comment";
import { getPostById } from "@/api/post";

export default {
  name: "PostDetail",
  data() {
    return {
      post: {}, //帖子信息
      userId: 0, //当前用户id
      userName: "", //当前用户姓名
      showSuccessDialog: false,
      showFailDialog: false,
      postId: 0, //当前帖子id
      courseInfo: {}, //课程信息
      types: ["初级", "中级", "高级"],
      currentContent: "", //评论内容
      currentReply: "", //回复内容
      comments: [], //评论
      isShow: false,
      msg: ""
    };
  },
  methods: {
    //评论帖子
    postComment() {
      const payload = {
        creatTime: null,
        content: this.currentContent,
        postId: this.post.id,
        userId: this.userId,
        userName: this.userName,
        repliedId: null,
        repliedUserName: null,
        repliedContent: null,
        haveRead: false
      };
      createComment(payload).then(res => {
        if (res.code === 1) {
          this.showSuccessDialog = true;
          setTimeout(() => {
            this.showSuccessDialog = false;
          }, 1000);
          setTimeout(() => {
            location.reload();
          }, 500);
        } else {
          this.showFailDialog = true;
          this.msg = res.msg;
          setTimeout(() => {
            this.showFailDialog = false;
          }, 1000);
        }
      });
    },
    //加载评论
    loadComment() {
      getCommentsOrderByTime(this.post.id).then(res => {
        this.comments = res;
      });
    },
    //回复评论
    postReply(comment) {
      const payload = {
        creatTime: null,
        content: this.currentReply,
        postId: Number(this.postId),
        userId: Number(this.userId),
        userName: this.userName,
        repliedId: comment.id,
        repliedUserName: comment.userName,
        repliedContent: comment.content,
        haveRead: false
      };
      createComment(payload).then(res => {
        if (res.code === 1) {
          this.showSuccessDialog = true;
          setTimeout(() => {
            this.showSuccessDialog = false;
          }, 1000);
          setTimeout(() => {
            location.reload();
          }, 500);
        } else {
          this.showFailDialog = true;
          this.msg = res.msg;
          setTimeout(() => {
            this.showFailDialog = false;
          }, 1000);
        }
      });
    },
    //展示回复框
    display(id) {
      let target = document.getElementsByClassName("textInput");
      let i = 0;
      for (let j = 0; j < target.length; j++) {
        target[j].style.display = "none";
      }
      for (; i < this.comments.length; i++) {
        if (this.comments[i].id === id) break;
      }
      if (target[i].style.display === "none") {
        target[i].style.display = "";
      } else {
        target[i].style.display = "none";
      }
    }
  },
  mounted() {
    const { postId } = this.$route.params;
    this.postId = postId;
    getPostById(postId).then(res => {
      this.post = res;
      const courseId = this.post.courseId;
      const uid = window.localStorage.getItem("userId");
      this.userId = uid;
      this.userName = window.localStorage.getItem("username");
      getCourseById({ uid, courseId }).then(res => {
        this.courseInfo = res;
      });
      this.loadComment();
    });
  }
};
</script>

<style scoped>
.courseMessage {
  width: 350px;
  position: absolute;
  right: 50px;
}
.comment {
  border-bottom: 1px solid #666666;
}
.form-group {
  width: 900px;
}
.form-control {
  float: left;
  font-size: 12px;
  display: inline-block;
  box-sizing: border-box;
  background-color: #f4f5f7;
  border: 1px solid #e5e9ef;
  overflow: auto;
  border-radius: 4px;
  color: #555;
  width: 800px;
  height: 65px;
  transition: 0s;
  padding: 5px 0;
  line-height: normal;
  outline: none;
}
.btm {
  float: left;
  width: 70px;
  height: 68px;
  background-color: #00a1d6;
  border: 1px solid #00a1d6;
  color: #fff;
  font-size: 14px;
  border-radius: 4px;
  cursor: pointer;
  text-align: center;
}
.form-reply {
  float: right;
  font-size: 12px;
  display: inline-block;
  box-sizing: border-box;
  background-color: #f4f5f7;
  border: 1px solid #e5e9ef;
  overflow: auto;
  border-radius: 4px;
  color: #555;
  width: 700px;
  height: 65px;
  transition: 0s;
  padding: 5px 0;
  line-height: normal;
  outline: none;
}
</style>
