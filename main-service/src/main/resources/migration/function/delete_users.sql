create or replace function delete_users_in_range(start_id int, end_id int)
    returns void as
$$
begin
    delete
    from complitech.auth_token
    where user_id in (select id from complitech.user where id between start_id and end_id);

    delete
    from complitech.user
    where id between start_id and end_id;
end;
$$ language plpgsql;

--есть второй вариант как эта функция будет работать
--при создании таблицы для токенов дописать ON DELETE CASCADE на внешнем ключе
--по итогу мы просто удаляем пользователей по id и на уровне БД записи со связанных таблиц будут дропаться