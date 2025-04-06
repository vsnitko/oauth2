export type MessageType = {
  senderId: string;
  senderName: string;
  avatar: string | undefined;
  messageText: string;
  mine: boolean;
};

export type MessageGroupType = {
  mine: MessageType["mine"];
  avatar: MessageType["avatar"];
  messageGroup: Array<MessageType>;
};
