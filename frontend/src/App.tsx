import React from "react";
import {AppBar, Container, CssBaseline, Grid, Link as MuiLink, Toolbar, Typography} from "@mui/material";
import EmojiNatureIcon from "@mui/icons-material/EmojiNature";
import {BrowserRouter, Link, Route, Routes} from "react-router-dom";
import Home from "./pages/Home";
import Monitoring from "./pages/Monitoring";
import NotFound from './pages/NotFound';
import NavMenu, {NavMenuItemProps} from "./compontents/NavMenu";

let pages: NavMenuItemProps[] = [
  {text: "文档", href: "https://peacetrue.github.io/bee", external: true},
  {text: "监控", href: "/monitoring", external: false},
];


function App() {
  return (
    <BrowserRouter>
      <CssBaseline/>
      {/* 展示全屏宽度 */}
      <Container maxWidth={false}>
        <AppBar>
          <Toolbar variant="dense">
            <EmojiNatureIcon/>
            <MuiLink component={Link} to="/" color="inherit" ml={1} underline="hover"
                     onClick={() => document.dispatchEvent(new Event("clearSelected"))}>
              <Typography variant="h6" color="inherit" component="div">Bee</Typography>
            </MuiLink>
            <NavMenu items={pages}/>
          </Toolbar>
        </AppBar>
        <Grid container flexDirection={"column"} minHeight={"100vh"}>
          <Grid item>
            <Toolbar/>
          </Grid>
          <Grid item flexGrow={1}>
            <Routes>
              <Route path="/" element={<Home/>}/>
              <Route path="/monitoring" element={<Monitoring/>}/>
              <Route path="*" element={<NotFound/>}/>
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
