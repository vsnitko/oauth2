export type MessageType = {
  senderId: string;
  senderName: string;
  avatarLink: string | undefined;
  messageText: string;
  mine: boolean;
};

export type MessageGroupType = {
  mine: MessageType["mine"];
  avatarLink: MessageType["avatarLink"];
  messageGroup: Array<MessageType>;
};
