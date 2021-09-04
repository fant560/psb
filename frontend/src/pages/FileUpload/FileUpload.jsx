import UploadFileForm from '../../components/UploadFileForm/UploadFileForm'
import { fileUploadStyles } from './FileUpload.styles'

const FileUpload = () => {
  return (
    <div css={fileUploadStyles}>
      <h1>Загрузить документ</h1>
      <br />
      <UploadFileForm />
    </div>
  )
}

export default FileUpload
