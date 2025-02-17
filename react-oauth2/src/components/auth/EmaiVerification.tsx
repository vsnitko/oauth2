import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";

export default function EmailVerification() {

  const location = useLocation();
  const [verified, setVerified] = useState(false);

  useEffect(() => {
    const queryParams = new URLSearchParams(location.search);
    const verificationStatus: boolean = queryParams.get('verified') === "true";
    setVerified(verificationStatus);
  }, [location]);

  return (
    <div>
      {verified ? (
        <h1>Email Verified Successfully!</h1>
      ) : (
        <h1>Verification Failed.</h1>
      )}
    </div>
  );
}

