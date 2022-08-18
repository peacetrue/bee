import React from 'react';
import './App.css';
import {AppBar, Container, CssBaseline, Grid, Link, Toolbar, Typography} from "@mui/material";
import EmojiNatureIcon from '@mui/icons-material/EmojiNature';

//@formatter:off
function App() {
  return (
    <>
      <CssBaseline/>
      <Container maxWidth={false}>
        <AppBar>
          <Toolbar variant="dense">
            <Link href="https://bee.peacetrue.cn" color="inherit"><EmojiNatureIcon/></Link>
            <Typography variant="h6" color="inherit" component="div" ml={1}>Bee</Typography>
            <Grid container ml={4} spacing={4}>
              <Grid item>
                <Link href="https://peacetrue.github.io/bee" color="inherit" underline="none" target={"_blank"}>文档</Link>
              </Grid>
              <Grid item>
                <Link href="https://peacetrue.github.io/bee" color="inherit" underline="none">监控</Link>
              </Grid>
            </Grid>
          </Toolbar>
        </AppBar>
        <Grid container flexDirection={"column"} minHeight={"100vh"}>
          <Grid item>
            <Toolbar/>
          </Grid>
          <Grid item flexGrow={1}
                container flexDirection={"column"}
                justifyContent={"center"} alignItems={"center"} spacing={4} >
            <Grid item><Typography variant={"h1"}>Bee</Typography></Grid>
            <Grid item><Typography variant={"h3"}>开发者助手</Typography></Grid>
            <Grid item><Typography variant={"h5"}>辅助技术人员日常开发，致力于在琐碎的细节中提升效率</Typography></Grid>
          </Grid>
          <Grid item textAlign={"center"} height={"80px"}>
            © 2022 peacetrue. 鄂ICP备20006312号
          </Grid>
        </Grid>
      </Container>
    </>
  );
}

export default App;
