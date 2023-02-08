import React from 'react';
import {Box} from "@mui/material";
import {useTranslation} from "react-i18next";

export default function Footer(props: Record<string, any>) {
  const {t} = useTranslation();
  return (
    <Box sx={{textAlign: "center", height: theme => theme.spacing(8)}}>
      © 2022 peacetrue. {t('icp')}
    </Box>
  );
}
