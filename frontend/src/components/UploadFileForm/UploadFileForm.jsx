import { useEffect, useRef } from 'react'
import { useMessage } from '../../hooks/message.hook'
import { useHttp } from '../../hooks/http.hook'
import { Form as AntForm, Input, Button, Upload } from 'antd'
import { UploadOutlined } from '@ant-design/icons'
import { useAuth } from '../../hooks/auth.hook'
import {
  submitButtonStyle,
  uploadFileFormStyles
} from './UploadFileForm.styles'

const UploadFileForm = ({ onlyFilePicker }) => {
  const message = useMessage()
  const formRef = useRef()
  const { loading, request, error, clearError } = useHttp()

  useEffect(() => {
    message(error, true)
    clearError()
  }, [error, message, clearError])

  const handleSubmit = async values => {
    const FD = new FormData()

    FD.append('file', values.upload[0].originFileObj)

    if (!onlyFilePicker) {
      FD.append('inn', values.INN)
      FD.append('nomenclature', values.fileRegister)
    }

    await request(
      onlyFilePicker ? '/api/uploadArchive' : '/api/uploadDocument',
      'POST',
      FD,
      {},
      true
    )
    formRef.current.resetFields()
  }

  const normFile = e => {
    if (Array.isArray(e)) {
      return e
    }
    return e && e.fileList
  }

  const props = {
    // beforeUpload: file => {
    //   const isJpgOrPng =
    //     file.type === 'audio/mpeg' ||
    //     file.type === 'video/mp4' ||
    //     file.type === 'audio/wav' ||
    //     file.type === 'audio/ogg'

    //   if (!isJpgOrPng) {
    //     message('Поддерживаемые форматы: mp3, mp4, wav, ogg', true)
    //     return Upload.LIST_IGNORE
    //   }

    //   return true
    // },
    customRequest: ({ file, onSuccess }) => {
      setTimeout(() => {
        onSuccess('ok')
      }, 0)
    },
    maxCount: 1
  }

  if (onlyFilePicker) {
    return (
      <AntForm
        name="validate_other"
        onFinish={handleSubmit}
        ref={formRef}
        css={uploadFileFormStyles}
      >
        <AntForm.Item
          name="upload"
          label="Загрузить архив"
          valuePropName="fileList"
          getValueFromEvent={normFile}
          rules={[
            {
              required: true,
              message: 'Загрузите архив'
            }
          ]}
        >
          <Upload {...props}>
            <Button icon={<UploadOutlined />}>Нажмите чтобы загрузить</Button>
          </Upload>
        </AntForm.Item>

        <AntForm.Item css={submitButtonStyle}>
          <Button htmlType="submit" type="primary" disabled={loading}>
            Отправить
          </Button>
        </AntForm.Item>
      </AntForm>
    )
  }

  return (
    <AntForm
      name="validate_other"
      onFinish={handleSubmit}
      ref={formRef}
      css={uploadFileFormStyles}
    >
      <AntForm.Item
        label="ИНН"
        name="INN"
        rules={[
          {
            required: true,
            message: 'Введите ИНН'
          }
        ]}
      >
        <Input maxLength={10} />
      </AntForm.Item>

      <AntForm.Item
        label="Номенклатура"
        name="fileRegister"
        rules={[
          {
            required: true,
            message: 'Введите номенклатуру'
          }
        ]}
      >
        <Input />
      </AntForm.Item>

      <AntForm.Item
        name="upload"
        label="Загрузить файл"
        valuePropName="fileList"
        getValueFromEvent={normFile}
        rules={[
          {
            required: true,
            message: 'Загрузите файл'
          }
        ]}
      >
        <Upload {...props}>
          <Button icon={<UploadOutlined />}>Нажмите чтобы загрузить</Button>
        </Upload>
      </AntForm.Item>

      <AntForm.Item css={submitButtonStyle}>
        <Button htmlType="submit" type="primary" disabled={loading}>
          Отправить
        </Button>
      </AntForm.Item>
    </AntForm>
  )
}

export default UploadFileForm
