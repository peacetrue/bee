import React, {Dispatch, SetStateAction} from "react";
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
import i18n from "./infrastructure/i18n"
import {I18nextProvider, useTranslation} from "react-i18next";
import LanguageSwitcher from "./compontents/LanguageSwitcher";
import {Feedback} from "./compontents/Feedback";
import Footer from "./pages/Footer";

interface HeaderProps {
  themeState: [number, Dispatch<SetStateAction<number>>]
}

function Header({themeState}: HeaderProps) {
  let {pathname} = useLocation();
  // 按第 2 个 / 分割：/function/conversion -> /function
  let initialActiveMenu = pathname === "/" ? false : pathname.split(/(?<=.+)\//, 1)[0];
  const [activeMenu, setActiveMenu] = React.useState<boolean | string>(initialActiveMenu || false);
  const {t} = useTranslation();
  return (
    <AppBar>
      <Toolbar variant="dense">
        <EmojiNatureIcon/>
        <MuiLink ml={1} component={Link} to="/" color="inherit" underline="none" variant={"h6"}
                 onClick={() => setActiveMenu(false)}>Bee</MuiLink>
        <Tabs sx={{ml: 4, "& .MuiTab-root": {fontSize: "medium"}}}
              value={activeMenu} onChange={(event, newValue) => setActiveMenu(newValue)}
              textColor={"inherit"} indicatorColor="secondary">
          <Tab label={t("function")} component={Link} to="/function/conversion" value={"/function"}/>
        </Tabs>
        <Stack flexDirection={"row"}>
          <Button variant="text" sx={{color: "inherit", fontSize: "medium"}}
                  href={"https://peacetrue.github.io/bee"} target={"_blank"}
                  endIcon={<LaunchRoundedIcon fontSize="inherit"/>}>{t("document")}</Button>
        </Stack>
        <Stack flexDirection={"row"}>
          <Button variant="text" sx={{color: "inherit", fontSize: "medium"}}
                  href={process.env.REACT_APP_SWAGGER_URL as string} target={"_blank"}
                  endIcon={<LaunchRoundedIcon fontSize="inherit"/>}>{t("swagger")}</Button>
        </Stack>
        <Stack flexGrow={1} flexDirection={"row"}>
          <Button variant="text" sx={{color: "inherit", fontSize: "medium"}}
                  href={process.env.REACT_APP_MONITOR_URL as string} target={"_blank"}
                  endIcon={<LaunchRoundedIcon fontSize="inherit"/>}>{t("monitor")}</Button>
        </Stack>
        {/* //TODO AppBar 下的颜色全部要使用继承，统一处理 */}
        <Stack sx={{flexDirection: "row", alignItems: "center", "& *": {color: "inherit"}}}>
          <IconButton sx={{color: "inherit"}} href={"https://github.com/peacetrue/bee"} target={"_blank"}>
            <GitHubIcon/>
          </IconButton>
          <ThemeSwitcher sx={{color: "inherit"}} value={themeState[0]}
                         onChange={(e, index) => themeState[1](index)}/>
          <LanguageSwitcher sx={{color: "inherit"}} value={i18n.resolvedLanguage}
                            onChange={(e, newValue) => i18n.changeLanguage(newValue)}/>
        </Stack>
      </Toolbar>
    </AppBar>
  );
}

function App() {
  const themeState = React.useState<number>(0);
  return (
    <I18nextProvider i18n={i18n} defaultNS={"bee"}>
      <SwitchableThemeProvider value={themeState[0]}>
        <CssBaseline/>
        {/* 展示全屏宽度 */}
        <Container maxWidth={false}>
          {/* 导航菜单。position="fixed" 不占用高度 */}
          <Header themeState={themeState}/>
          {/* 主体 */}
          <Stack sx={{minHeight: "100vh"}}>
            {/* 占位：https://mui.com/zh/material-ui/react-app-bar/#fixed-placement */}
            <Toolbar variant="dense"/>
            <Box sx={{padding: 2, flexGrow: 1}}>
              {/* 路由出口 */}
              <Outlet/>
            </Box>
            {/* 页脚 */}
            <Footer/>
          </Stack>
        </Container>
        <Feedback/>
      </SwitchableThemeProvider>
    </I18nextProvider>
  );
}

export default App;
