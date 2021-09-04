import React, { useContext, useEffect, useState } from 'react'
import ReactMarkdown from 'react-markdown'
import { useParams, Link } from 'react-router-dom'
import { Button, Divider, Spin } from 'antd'
import { ArrowLeftOutlined, DownloadOutlined } from '@ant-design/icons'
// import { DOCUMENTS_PAGE } from '../../Routes/routes.paths'
import { documentHeaderStyle, documentTextStyle } from './Document.styles'
import { dividerStyle } from '../../components/DocumentsList/DocumentsList.style'
import { useHttp } from '../../hooks/http.hook'
import { AuthContext } from '../../contexts/AuthContext'

const Document = () => {
  const { loading, request } = useHttp()
  const [document, setDocument] = useState({})
  const auth = useContext(AuthContext)
  const params = useParams()

  // useEffect(() => {
  //   const getDocumentById = async () => {
  //     const data = await request(`/ml/document/${params.id}`, 'GET', null, {
  //       Authorization: `Bearer ${auth.accessToken}`
  //     })
  //     setDocument(data)
  //   }

  //   getDocumentById()
  // }, [params.id, request, auth.accessToken])

  return <h1>Документ {params.id}</h1>
}

export default Document
