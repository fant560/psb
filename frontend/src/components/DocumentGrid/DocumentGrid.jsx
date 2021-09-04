import { Badge, Card, List } from 'antd'
import { useHistory, useRouteMatch } from 'react-router-dom'
import { cardStyles } from './DocumentGrid.styles'
import moment from 'moment'

const DocumentGrid = ({ documents, isLoading }) => {
  const history = useHistory()
  const match = useRouteMatch()

  return (
    <List
      grid={{ gutter: 16, column: 4 }}
      dataSource={documents}
      loading={isLoading}
      renderItem={item => (
        <List.Item
          css={cardStyles}
          onClick={() => history.push(`${match.url}/${item.id}`)}
        >
          <Badge.Ribbon
            text={item.state}
            color={(() => {
              switch (item.state) {
                case 'Отправлен на распознавание':
                  return 'yellow'
                case 'Успешно распознан':
                  return 'green'
                case 'Ошибка распознавания':
                  return 'red'
                default:
                  return 'blue'
              }
            })()}
          >
            <Card title={item.fileName} loading={isLoading}>
              <div>Заголовок: {item?.title ? item?.title : 'нет'}</div>
              <div>ИНН: {item?.inn}</div>
              <div>Номенклатура: {item?.documentNomenclatureId}</div>
              <div>
                Дата создания:{' '}
                {moment(item?.dateOfUploading).utc().format('DD-MM-YYYY')}
              </div>
            </Card>
          </Badge.Ribbon>
        </List.Item>
      )}
    />
  )
}
export default DocumentGrid
