import React from 'react';
import {Grid, Typography} from "@mui/material";
import {useTranslation} from "react-i18next";

export default function Home(props: Record<string, any>) {
  const {t} = useTranslation();
  return (
    <Grid mt={4} container flexDirection={"column"}
          justifyContent={"center"} alignItems={"center"} spacing={4}>
      <Grid item><Typography variant={"h1"}>Bee</Typography></Grid>
      <Grid item><Typography variant={"h3"}>{t("title")}</Typography></Grid>
      <Grid item><Typography variant={"h5"}>{t("description")}</Typography></Grid>
    </Grid>
  );
}
