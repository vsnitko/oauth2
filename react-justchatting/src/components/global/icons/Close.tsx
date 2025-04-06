import React from 'react';
import {IconType} from "./IconType.ts";

const Close: React.FC<IconType> = (
  {
    className,
    onClick
  }
) => (
  <svg
    onClick={onClick}
    className={className}
    width="24"
    height="24"
    viewBox="0 0 24 24"
    fill="none"
    stroke="currentColor"
    strokeWidth="2"
    strokeLinecap="round"
    strokeLinejoin="round"
  >
    <path d="M18 6 6 18" />
    <path d="m6 6 12 12" />
  </svg>

);

export default Close;

