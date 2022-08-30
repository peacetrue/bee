import React from "react";
import {
  AppBar,
  Box,
  Button,
  Container,
  CssBaseline,
  IconButton,
  Link as MuiLink,
  Stack,
  Tab,
  Tabs,
  Toolbar
} from "@mui/material";
import EmojiNatureIcon from "@mui/icons-material/EmojiNature";
import {Link, Outlet, useLocation} from "react-router-dom";
import LaunchRoundedIcon from '@mui/icons-material/LaunchRounded';
import ThemeSwitcher from "./compontents/ThemeSwitcher";
import SwitchableThemeProvider from "./compontents/SwitchableThemeProvider";
import GitHubIcon from '@mui/icons-material/GitHub';

function App() {
  let location = useLocation();
  console.info("location: ", location)
  let initialActiveMenu = location.pathname === "/" ? false
    : location.pathname.split(/(?<=.+)\//, 1)[0];
  const [activeMenu, setActiveMenu] = React.useState<string | boolean>(initialActiveMenu || false);
  const [themeIndex, setThemeIndex] = React.useState<number>(0);
  return (
    <SwitchableThemeProvider value={themeIndex}>
      <CssBaseline/>
      {/*<BrowserRouter>*/}
      {/* 展示全屏宽度 */}
      <Container maxWidth={false}>
        {/* 导航菜单。position="fixed" 不占用高度 */}
        <AppBar>
          <Toolbar variant="dense">
            <EmojiNatureIcon/>
            <MuiLink ml={1} component={Link} to="/" color="inherit" underline="none" variant={"h6"}
                     onClick={() => setActiveMenu(false)}>Bee</MuiLink>
            <Tabs sx={{ml: 4, "& .MuiTab-root": {fontSize: "medium"}}}
                  value={activeMenu} onChange={(event, newValue) => setActiveMenu(newValue)}
                  textColor={"inherit"} indicatorColor="secondary">
              <Tab label="功能" component={Link} to="/function" value={"/function"}/>
              <Tab label="监控" component={Link} to="/monitoring" value={"/monitoring"}/>
            </Tabs>
            <Stack flexGrow={1} flexDirection={"row"}>
              <Button variant="text" sx={{color: "inherit", fontSize: "medium"}}
                      href={"https://peacetrue.github.io/bee"} target={"_blank"}
                      endIcon={<LaunchRoundedIcon fontSize="inherit"/>}>文档</Button>
            </Stack>
            {/* //TODO AppBar 下的颜色全部要使用继承，统一处理 */}
            <Stack sx={{flexDirection: "row", alignItems: "center", "& *": {color: "inherit"}}}>
              <IconButton sx={{color: "inherit"}} href={"https://github.com/peacetrue/bee"} target={"_blank"}><GitHubIcon/></IconButton>
              <ThemeSwitcher sx={{color: "inherit"}} value={themeIndex} onChange={(e, index) => setThemeIndex(index)}/>
            </Stack>
          </Toolbar>
        </AppBar>
        {/* 主体 */}
        <Stack sx={{minHeight: "100vh"}}>
          {/* 占位：https://mui.com/zh/material-ui/react-app-bar/#fixed-placement */}
          <Toolbar variant="dense"/>
          <Box sx={{padding: 2, flexGrow: 1}}>
            {/* 路由出口 */}
            <Outlet/>
          </Box>
          {/* 页脚 */}
          <Box sx={{textAlign: "center", height: "64px"}}>
            © 2022 peacetrue. 鄂ICP备20006312号
          </Box>
        </Stack>
      </Container>
      {/*</BrowserRouter>*/}
    </SwitchableThemeProvider>
  );
}

export default App;
