export type MessageType = {
  senderId: number;
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
