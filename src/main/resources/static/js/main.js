'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0',
    '#b232b2', '#8B4F1D', '#EA813D', '#FF5050',
    '#FF895A', '#FFA500', '#FFE146', '#FF28A7',
    '#FFAAAF', '#147814', '#64D2D2', '#2828CD',
    '#1E82FF', '#14D3FF'
];

function connect(event) {
    username = document.querySelector('#name').value.trim(); // querySelector는 document 내의 요소를 검색하고 여러 결과를 찾았다면 첫 번째 요소만 리턴해주는 메소드입니다

    if(username) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        var socket = new SockJS('/ws'); // 주소..
        stompClient = Stomp.over(socket); // stomp 프로토콜 위에 sockJS가 돌아가도록 Stomp.over()안에 socket을 넣어준다.

        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();  // preventDefault() 메서드는 어떤 이벤트를 명시적으로 처리하지 않은 경우, 해당 이벤트에 대한 사용자 에이전트의 기본 동작을 실행하지 않도록 지정합니다.
}


function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived); // 구독 요청. 목록에 추가 //onMessageReceived - callBack 메소드 ( 아래에 있음)

    // Tell your username to the server
    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({sender: username, type: 'JOIN'}) // JSON.stringify() 메서드는 JavaScript 값이나 객체를 JSON 문자열로 변환합니다.
    )

    connectingElement.classList.add('hidden'); // connectingElement: 클래스를 추가하려는 HTML 요소, classList.add()그 중 하나입니다. 이 메소드는 선택한 요소의 클래스 속성에 하나 이상의 클래스를 추가하는 데 사용
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT'
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body); // body객체에서 메시지 가져오기

    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        // html 만들기 위한 코드
        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    // html 만들기 위한 코드
    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight; // 메시지를 항상 위에 두기 위한 것
}


// 아바타 색깔 선정
function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}

usernameForm.addEventListener('submit', connect, true)  // addEventListener()는 document의 특정요소(Id,class,tag 등등..) event(ex - click하면 함수를 실행하라, 마우스를 올리면 함수를 실행하라 등등.. )를 등록할 때 사용합니다.
messageForm.addEventListener('submit', sendMessage, true)
