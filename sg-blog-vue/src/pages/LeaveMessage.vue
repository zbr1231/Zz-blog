<!-- 留言页面 -->
<template>
<div>
    <sg-nav></sg-nav>
    <div class="container">
        <el-row :gutter="30">
            <el-col :sm="24" :md="24">
                <sg-barrage></sg-barrage>
            </el-col>
        </el-row>
        <el-row :gutter="30">
            
        </el-row>
        <!-- <el-button @click="send" type="primiary">发送</el-button> -->
        <el-row :gutter="30">
            <el-col :sm="24" :md="16">
                <sg-message></sg-message>
            </el-col>
            <el-col :sm="24" :md="8">
				<sg-rightlist></sg-rightlist>
			</el-col>
        </el-row>
    </div>
</div>
</template>

<script>
import header from '../components/header.vue'
import rightlist from '../components/rightlist.vue'
import reward from '../components/reward.vue'
import barrage from '../components/barrage.vue'
import message from '../components/leaveMessage.vue'
export default {
    name: 'Reward',
    data() { //选项 / 数据
        return {

        }
    },
    methods: { //事件处理器
        buildConn(){
            //建立连接
            this.goeasy.connect({
                id:"001", //pubsub选填，im必填，最大长度60字符
                data:{"avatar":"/www/xxx.png","nickname":"Neo"}, //必须是一个对象，pubsub选填，im必填，最大长度300字符，用于上下线提醒和查询在线用户列表时，扩展更多的属性
                onSuccess: function () {  //连接成功
                console.log("GoEasy connect successfully.") //连接成功
                },
                onFailed: function (error) { //连接失败
                console.log("Failed to connect GoEasy, code:"+error.code+ ",error:"+error.content);
                },
                onProgress:function(attempts) { //连接或自动重连中
                console.log("GoEasy is connecting", attempts);
                }
            });
        },
        pubsub() {
                var pubsub = this.goeasy.pubsub;
                pubsub.subscribe({
                    channel: "my_channel",//替换为您自己的channel
                    onMessage: function (message) {
                        console.log("Channel:" + message.channel + " content:" + message.content);
                    },
                    onSuccess: function () {
                        console.log("Channel订阅成功。");
                    },
                    onFailed: function (error) {
                        console.log("Channel订阅失败, 错误编码：" + error.code + " 错误信息：" + error.content)
                    }
                });
        },
        send(){
            var pubsub = this.goeasy.pubsub;
            pubsub.publish({
                channel: "my_channel",//替换为您自己的channel
                message: "Hello GoEasy!",//替换为您想要发送的消息内容
                onSuccess:function(){
                    console.log("消息发布成功。");
                },
                onFailed: function (error) {
                    console.log("消息发送失败，错误编码："+error.code+" 错误信息："+error.content);
                }
            });

        }
    },
    components: { //定义组件
        'sg-nav': header,
        'sg-reward': reward,
        'sg-rightlist': rightlist,
        'sg-barrage': barrage,
        'sg-message': message
    },
    created() { //生命周期函数
        // this.buildConn();
        // this.pubsub()
    }
}
</script>

<style>

</style>
    