import {
  Box,
  Button,
  FormControl,
  FormHelperText,
  InputLabel,
  MenuItem,
  Select,
  Stack,
  TextField,
  Typography
} from "@mui/material";
import React, {useEffect} from "react";
import Axios from "../../infrastructure/Axios";
import {Controller, FieldValues, useForm} from "react-hook-form";
import {UseFormReturn} from "react-hook-form/dist/types";
import {yupResolver} from '@hookform/resolvers/yup';
import * as yup from "yup";

interface DataPanelProps {
  label: string;
  name: string;
  form: UseFormReturn;
  formats: string[];
}

function DataPanel(props: DataPanelProps) {
  const {label, name, form: {control}, formats} = props;
  return (
    <Stack spacing={2}>
      <Box>
        <Typography variant={"subtitle1"}>{label}</Typography>
      </Box>
      <Box>
        <Controller
          name={`${name}.format`} control={control}
          render={({field, fieldState, formState}) =>
            <FormControl fullWidth error={!!fieldState.error}>
              <InputLabel>数据格式</InputLabel>
              <Select {...field} label={"数据格式"}>
                <MenuItem value=""><em>None</em></MenuItem>
                {formats.map(item => <MenuItem key={item} value={item}>{item}</MenuItem>)}
              </Select>
              <FormHelperText>{fieldState.error?.message}</FormHelperText>
            </FormControl>
          }/>
      </Box>
      <Box>
        <Controller
          name={`${name}.content`} control={control}
          render={({field, fieldState, formState}) =>
            <TextField label="数据内容" multiline minRows={20} fullWidth
                       {...field} error={!!fieldState.error} helperText={fieldState.error?.message}
            />
          }
        />
      </Box>
    </Stack>
  );
}

const user_json = `{
  "name" : "张三",
  "age" : 18,
  "roles" : [ {
    "name" : "admin",
    "remark" : "管理员"
  }, {
    "name" : "employee",
    "remark" : "员工"
  } ],
  "tags" : [ "goodness", "tall" ]
}`;

export default function Conversion() {
  const [formats, setFormats] = React.useState<string[]>([]);
  useEffect(() => {
    Axios.get("/conversion/formats").then(response => setFormats(response.data));
  }, []);

  const schema = yup.object({
    "source": yup.object({
      "format": yup.string().required(),
      "content": yup.string().required().max(1024 * 512),
    }),
    "target": yup.object({
      "format": yup.string().required(),
    })
  });

  const form = useForm({
    resolver: yupResolver(schema),
    defaultValues: {
      source: {format: "json", content: user_json},
      target: {format: "yaml", content: ""}
    } as FieldValues
  });

  return (
    <Stack spacing={1}>
      <Box><Typography variant={"h6"}>不同格式的数据相互转换</Typography></Box>
      <Box component={"form"} onSubmit={form.handleSubmit((data) => {
        //target 的内容不需要传回后台
        data = {source: data.source, target: {format: data.target.format}};
        Axios.post("/conversion", data, {responseType: "text"})
          .then(response => {
            let data = response.data;
            typeof data !== 'string' && (data = JSON.stringify(data));
            return form.setValue("target.content", data);
          });
      })}>
        <Stack sx={{flexDirection: "row", alignItems: "center"}}>
          <Box sx={{flexGrow: 1}}>
            <DataPanel label={"原始数据"} name={"source"} form={form} formats={formats}/>
          </Box>
          <Box sx={{m: 1}}>
            <Button type="submit" size="medium" variant={"outlined"}> &gt; </Button>
          </Box>
          <Box sx={{flexGrow: 1}}>
            <DataPanel label={"目标数据"} name={"target"} form={form} formats={formats}/>
          </Box>
        </Stack>
      </Box>
    </Stack>
  );
}
