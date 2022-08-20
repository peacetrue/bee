import React from 'react';
import './App.css';
import {AppBar, Container, CssBaseline, Grid, Link as MuiLink, Toolbar, Typography} from "@mui/material";
import EmojiNatureIcon from '@mui/icons-material/EmojiNature';
import {BrowserRouter, Link, Route, Routes} from "react-router-dom";
import Home from "./pages/Home";
import Monitoring from "./pages/Monitoring";
import NotFound from './pages/NotFound';

//@formatter:off
function App() {
  return (
    <BrowserRouter>
      <CssBaseline/>
      <Container maxWidth={false}>
        <AppBar>
          <Toolbar variant="dense">
            <MuiLink component={Link} to="/" color="inherit"><EmojiNatureIcon/></MuiLink>
            <Typography variant="h6" color="inherit" component="div" ml={1}>Bee</Typography>
            <Grid container ml={4} spacing={4}>
              <Grid item>
                <MuiLink href="https://peacetrue.github.io/bee" color="inherit" underline="none" target={"_blank"}>文档</MuiLink>
              </Grid>
              <Grid item>
                {/*https://stackoverflow.com/questions/63216730/can-you-use-material-ui-link-with-react-router-dom-link*/}
                <MuiLink component={Link} to="/monitoring" color="inherit" underline={"none"}>监控</MuiLink>
              </Grid>
            </Grid>
          </Toolbar>
        </AppBar>
        <Grid container flexDirection={"column"} minHeight={"100vh"}>
          <Grid item>
            <Toolbar/>
          </Grid>
          <Grid item flexGrow={1}>
              <Routes>
                <Route path="/" element={<Home />}/>
                <Route path="/monitoring" element={<Monitoring />} />
                <Route path="*" element={<NotFound />} />
              </Routes>
          </Grid>
          <Grid item textAlign={"center"} height={"80px"}>
            © 2022 peacetrue. 鄂ICP备20006312号
          </Grid>
        </Grid>
      </Container>
    </BrowserRouter>
  );
}

export default App;
