import {useEffect, useState} from "react";
import {Alert, Snackbar} from "@mui/material";

export function Feedback({}: FeedbackProps) {
  const vertical = "top", horizontal = "center";
  let [open, setOpen] = useState(false);
  let [message, setMessage] = useState("<unknown>");
  useEffect(() => {
    document.addEventListener("feedback.show", e => {
      setMessage((e as CustomEvent).detail);
      setOpen(true);
    });
  }, []);
  return (
    <Snackbar
      anchorOrigin={{vertical, horizontal}}
      open={open}
      onClose={(event, reason) => setOpen(false)}
      key={vertical + horizontal}>
      <Alert severity="error" onClose={() => setOpen(false)}>{message}</Alert>
    </Snackbar>
  )
}

export interface FeedbackProps {
}
