// import Main from '../pages/Main/Main'
// import Document from '../pages/Document/Document'
import Login from '../pages/Login/Login'
import Register from '../pages/Register/Register'
import PSBMain from '../pages/PSBMain/PSBMain'
import {
  // DOCUMENT_PAGE,
  // DOCUMENTS_PAGE,
  LOGIN_PAGE,
  REGISTER_PAGE,
  PSB_DOCUMENTS_PAGE,
  PSB_FILE_UPLOAD,
  PSB_ARCHIVE_UPLOAD,
  PSB_DOCUMENTS_LIST,
  PSB_DOCUMENT
} from './routes.paths'
import ArchiveUpload from '../pages/ArchiveUpload/ArchiveUpload'
import FileUpload from '../pages/FileUpload/FileUpload'
import DocumentsList from '../pages/DocumentsList/DocumentsList'
import Document from '../pages/Document/Document'

export const routesForAuthUsers = [
  // {
  //   path: DOCUMENTS_PAGE,
  //   component: <Main />,
  //   exact: true
  // },
  // {
  //   path: DOCUMENT_PAGE,
  //   component: <Document />,
  //   exact: false
  // },
  {
    path: PSB_DOCUMENTS_PAGE,
    component: <PSBMain />,
    exact: false
  }
]

export const insideAppRoutes = [
  {
    path: PSB_DOCUMENTS_LIST,
    component: <DocumentsList />,
    exact: true
  },
  {
    path: PSB_FILE_UPLOAD,
    component: <FileUpload />,
    exact: false
  },
  {
    path: PSB_ARCHIVE_UPLOAD,
    component: <ArchiveUpload />,
    exact: false
  },
  {
    path: PSB_DOCUMENT,
    component: <Document />,
    exact: false
  }
]

export const routesForNotAuthUsers = [
  {
    path: LOGIN_PAGE,
    component: <Login />,
    exact: false
  },
  {
    path: REGISTER_PAGE,
    component: <Register />,
    exact: false
  }
]
