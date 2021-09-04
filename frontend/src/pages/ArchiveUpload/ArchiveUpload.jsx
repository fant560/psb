import UploadFileForm from '../../components/UploadFileForm/UploadFileForm'

const ArchiveUpload = () => {
  return (
    <div>
      <h1>Загрузить архив документов</h1>
      <br />
      <UploadFileForm onlyFilePicker={true} />
    </div>
  )
}

export default ArchiveUpload
