<!-- 弹幕 -->
<template>
  <div class="barrages-drop" >
    <vue-baberrage
      :isShow="barrageIsShow"
      :barrageList="barrageList"
      :maxWordCount="maxWordCount"
      :throttleGap="throttleGap"
      :loop="barrageLoop"
      :boxHeight="boxHeight"
      :messageHeight="messageHeight"
    >
    </vue-baberrage>
  </div>
</template>
<script>
import Vue from 'vue';
  import { vueBaberrage, MESSAGE_TYPE } from 'vue-baberrage';
  import {messageList,addMessage} from '../api/message'
  import Public from '../public.js'
  Vue.use(vueBaberrage);
  export default {
    name: 'Barrages',
    data() {
      return {
        msg: '~',
        barrageIsShow: true,
        messageHeight: 3,
        boxHeight: 150,
        barrageLoop: true,
        maxWordCount: 3,
        throttleGap: 4000,
        barrageList: [],
        avarar: 'https://zbr1231.oss-cn-beijing.aliyuncs.com/2022/10/03/6be16b2428f54dadbd322c1a1f79f5b3.jpg'
      };
    },
    created() {
      let that = this
        Public.$on('addOne', val=>{
          this.addOne(val)
        })
    },
    mounted() {
      this.getList();
    },
    methods: {
      getList(){
        messageList().then((response)=>{
          response.forEach(element => {
            let e = {
              id: element.id,
              avarar: this.avarar,
              msg: element.content,
              time: element.id%7+7,
              barrageStyle: ''
            }
            this.barrageList.push(e)
          });
        })
      },
      addToList(e) {
        this.barrageList.push(e)
        console.log(this.barrageList)
        // let list = [
        //   {
        //     id: 1,
        //     avatar: '',
        //     msg: "人生若只如初见",
        //     time: 1,
        //     barrageStyle: 'red'
        //   },
        //   {
        //     id: 2,
        //     avatar: '',
        //     msg: "何事秋风悲画扇",
        //     time: 2,
        //     barrageStyle: 'green'
        //   },
        //   {
        //     id: 3,
        //     avatar: '',
        //     msg: "等闲变却故人心",
        //     time: 3,
        //     barrageStyle: 'normal'
        //   },
        //   {
        //     id: 4,
        //     avatar: '',
        //     msg: "却道故人心易变",
        //     time: 4,
        //     barrageStyle: 'blue'
        //   },
        //   {
        //     id: 5,
        //     avatar: '',
        //     msg: "骊山语罢清宵半",
        //     time: 5,
        //     barrageStyle: 'yellow'
        //   },
        //   {
        //     id: 6,
        //     avatar: '',
        //     msg: "泪雨霖铃终不怨",
        //     time: 6,
        //     barrageStyle: 'normal'
        //   },
        //   {
        //     id: 7,
        //     avatar: '',
        //     msg: "何如薄幸锦衣郎",
        //     time: 7,
        //     barrageStyle: 'red'
        //   },
        //   {
        //     id: 8,
        //     avatar: '',
        //     msg: "比翼连枝当日愿",
        //     time: 8,
        //     barrageStyle: 'normal'
        //   },
        // ];
        // list.forEach((v) => {
        //   this.barrageList.push({
        //     id: v.id,
        //     avatar: v.avatar,
        //     msg: v.msg,
        //     time: v.time,
        //     type: MESSAGE_TYPE.NORMAL,
        //     barrageStyle: v.barrageStyle
        //   });
        // });
      }
      ,
      addOne(data){
        let e = {
              id: data.id,
              avarar: 'https://zbr1231.oss-cn-beijing.aliyuncs.com/2022/10/03/6be16b2428f54dadbd322c1a1f79f5b3.jpg',
              msg: data.content,
              time: data.id%7+7,
              barrageStyle: ""
            }
            this.barrageList.push(e)
      }
    }
  };
</script>
<style lang="scss" scoped>
.barrages-drop {
    height: 150px;
    //头像
    /deep/ .baberrage-item .normal .baberrage-avatar {
      width: 0px;
      height: 0px;
      background-color: black;
    }
    /*弹幕消息框*/
    /deep/ .baberrage-item .normal {
      padding: 5px 10px 5px 0px;
      min-width: 60px;
      background-color: #97d0e3;
      border: #a9daef solid 2px;
      opacity: 0.7;
      text-align: center;
    }
    /*文字*/
    /deep/ .baberrage-item .normal .baberrage-msg {
      
      width: 100%;
      text-align: center;
      font-size: 14px;
      line-height: 25px;
    }
  /deep/ .baberrage-lane{
    /deep/ .blue .normal{
      border-radius: 100px;
      background: #e6ff75;
      color: #fff;
    }
    /deep/ .green .normal{
      border-radius: 100px;
      background: #75ffcd;
      color: #fff;
    }
    /deep/ .red .normal{
      border-radius: 100px;
      background: #e68fba;
      color: #fff;
    }
    /deep/ .yellow .normal{
      border-radius: 100px;
      background: #dfc795;
      color: #fff;
    }
    .baberrage-stage {
      position: absolute;
      width: 100%;
      height: 212px;
      overflow: hidden;
      top: 0;
      margin-top: 130px;
    }
  }
  
}
</style>
  
  
  
  