import React from 'react';
import {Route, Routes, useLocation} from "react-router-dom";
import Main from "./function/Main";
import Home from "./function/Home";
import NotFound from "./NotFound";

export default function Function(props: {}) {
  return (
    <Routes>
      <Route path="/" element={<Main/>}>
        <Route index element={<Home/>}/>
        <Route path="*" element={<NotFound/>}/>
      </Route>
    </Routes>
  );
}
