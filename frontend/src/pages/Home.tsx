import React from 'react';
import {Grid, Typography} from "@mui/material";

export default function Home(props: Record<string, any>) {
  return (
    <Grid mt={4} container flexDirection={"column"}
          justifyContent={"center"} alignItems={"center"} spacing={4}>
      <Grid item><Typography variant={"h1"}>Bee</Typography></Grid>
      <Grid item><Typography variant={"h3"}>开发者助手</Typography></Grid>
      <Grid item><Typography variant={"h5"}>辅助技术人员日常开发，致力于在琐碎的细节中提升效率</Typography></Grid>
    </Grid>
  );
}
