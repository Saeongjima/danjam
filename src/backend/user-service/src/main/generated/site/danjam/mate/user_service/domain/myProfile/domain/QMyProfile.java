package site.danjam.mate.user_service.domain.myProfile.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMyProfile is a Querydsl query type for MyProfile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMyProfile extends EntityPathBase<MyProfile> {

    private static final long serialVersionUID = -1248246136L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMyProfile myProfile = new QMyProfile("myProfile");

    public final site.danjam.mate.user_service.global.common.entity.QBaseTimeEntity _super = new site.danjam.mate.user_service.global.common.entity.QBaseTimeEntity(this);

    public final StringPath birth = createString("birth");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDateTime = _super.createdDateTime;

    public final NumberPath<Integer> entryYear = createNumber("entryYear", Integer.class);

    public final StringPath greeting = createString("greeting");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath major = createString("major");

    public final StringPath mbti = createString("mbti");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDateTime = _super.modifiedDateTime;

    public final StringPath profileImgUrl = createString("profileImgUrl");

    public final site.danjam.mate.user_service.domain.user.domain.QUser user;

    public QMyProfile(String variable) {
        this(MyProfile.class, forVariable(variable), INITS);
    }

    public QMyProfile(Path<? extends MyProfile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMyProfile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMyProfile(PathMetadata metadata, PathInits inits) {
        this(MyProfile.class, metadata, inits);
    }

    public QMyProfile(Class<? extends MyProfile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new site.danjam.mate.user_service.domain.user.domain.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

