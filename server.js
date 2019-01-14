var io = require('socket.io');
var express = require('express');
var app = express()
  , server = require('http').createServer(app)
  , io = io.listen(server);
var arr = [];
server.listen(3000);

io.sockets.on('connection', function (socket) {
	console.log(socket.id);
	arr.push(socket.id);
	console.log('hey');
	
	socket.on('afterNews', function(data){
		console.log(data);
	});
	socket.on('messageSent',function(data){
		
		console.log(data);
		for(var i=0;i < arr.length;i++)
		{
			if(arr[i] !==socket.id){
				socket.to(arr[i]).emit('messageReceive', {hi:data});
				console.log(arr[i]);
			}
		}
		
	});
	socket.on('disconnect', function(data){
		console.log('disconnect');
		arr.pop(socket.id);
	});
});



